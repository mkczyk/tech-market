const data = technologies;
const width = 500;
const height = 500;

const svg = d3.select("#svg-container")
    .attr("width", width)
    .attr("height", height);

color = d3.scaleOrdinal(d3.schemeCategory10)

const nodes = data.nodes.map(d => Object.create(d));
const index = new Map(nodes.map(d => [d.id, d]));
const links = data.links.map(d => Object.assign(Object.create(d), {
    source: index.get(d.source),
    target: index.get(d.target)
}));

netClustering.cluster(nodes, links);

const layout = cola.d3adaptor(d3)
    .size([width, height])
    .nodes(nodes)
    .links(links)
    .linkDistance(d => 30 + Math.pow(d.value, -1) * 200)
    .start(30);

const link = svg.append("g")
    .attr("stroke", "#999")
    .attr("stroke-opacity", 0.6)
    .selectAll("line")
    .data(links)
    .enter()
    .append("line")
    .attr("stroke-width", d => Math.sqrt(d.value));

link.append("title")
    .text(d => d.value);

const node = svg.selectAll("g.node")
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
    .text(d => d.id);

layout.on("tick", () => {
    link
        .attr("x1", d => d.source.x)
        .attr("y1", d => d.source.y)
        .attr("x2", d => d.target.x)
        .attr("y2", d => d.target.y);

    node
        .attr("transform", d => 'translate(' + [d.x, d.y] + ')')
});
