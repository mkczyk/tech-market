let svg = d3.select("#svg-container")
	.attr("width", 400)
	.attr("height", 200);

const size = 100;
svg.append('rect')
	.attr('x', 50)
	.attr('y', 50)
	.attr('width', size)
	.attr('height', () => size / 2)
	.attr('fill', 'green');
