const data = technologies;
const width = 1500;
const height = 800;

const svg = d3.select("#svg-container")
    .attr("width", width)
    .attr("height", height);

color = d3.scaleOrdinal(d3.schemeCategory10)

const nodes = data.nodes.map(d => Object.create(d));
const index = new Map(nodes.map(d => [d.id, d]));
const links = data.links.map(d => Object.assign(Object.create(d), {
    source: index.get(d.source),
    target: index.get(d.target),
    name: `${d.source}-${d.target}`
}));

netClustering.cluster(nodes, links);

const layout = cola.d3adaptor(d3)
    .size([width, height])
    .nodes(nodes)
    .links(links)
    .linkDistance(d => 30 + Math.pow(d.value, -1) * 200)
    .start(30);

const view = svg.append("g");

const link = view.append("g")
    .selectAll("line")
    .data(links)
    .enter()
    .append("g");

const line = link.append("line")
    .attr("stroke", "#777")
    .attr("stroke-opacity", 0.6)
    .attr("stroke-width", d => Math.sqrt(d.value))

const border = link.append("line")
    .attr("stroke", "#000")
    .attr("stroke-opacity", 0)
    .attr("stroke-width", d => 15);

border.append("title")
    .text(d => `${d.value} (${d.name})`);

const node = view.selectAll("g.node")
    .data(nodes)
    .enter()
    .append("g");

const circle = node.append("circle")
    .attr("stroke", "#fff")
    .attr("stroke-width", 1)
    .attr("r", 10)
    .attr("fill", d => color(d.cluster))
    .call(layout.drag);

circle.append("title")
    .text(d => d.id);

node.append("text")
    .attr("dx", d => 10)
    .attr("dy", d => 15)
    .attr("font-size", "12px")
    .attr("stroke", "#fff")
    .attr("stroke-width", 2)
    .attr("paint-order", "stroke fill")
    .text(d => d.id)
    .on("mousedown", d => d3.event.stopPropagation());

svg.call(d3.zoom()
    .extent([[0, 0], [width, height]])
    .scaleExtent([0.5, 5])
    .on("zoom", zoomed));

function zoomed() {
    view.attr("transform", d3.event.transform);
}

layout.on("tick", () => {
    [line, border].forEach(element =>
        element
            .attr("x1", d => d.source.x)
            .attr("y1", d => d.source.y)
            .attr("x2", d => d.target.x)
            .attr("y2", d => d.target.y)
    );

    node
        .attr("transform", d => 'translate(' + [d.x, d.y] + ')')
});
