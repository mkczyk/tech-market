const technologies = ({
    nodes: [
        {id: "bash", group: 0},
        {id: "Linux", group: 0},
        {id: "SQL", group: 1},
        {id: "Java", group: 2},
        {id: "Spring", group: 2},
        {id: "Hibernate", group: 2},
        {id: "JPA", group: 2},
        {id: "HTML", group: 3},
        {id: "CSS", group: 3}
    ],
    links: [
        {source: "bash", target: "Linux", value: 20},
        {source: "SQL", target: "Linux", value: 10},
        {source: "SQL", target: "Java", value: 4},
        {source: "Java", target: "Spring", value: 10},
        {source: "SQL", target: "Hibernate", value: 8},
        {source: "SQL", target: "JPA", value: 8},
        {source: "Java", target: "Hibernate", value: 10},
        {source: "Spring", target: "Hibernate", value: 15},
        {source: "JPA", target: "Hibernate", value: 20},
        {source: "SQL", target: "HTML", value: 2},
        {source: "HTML", target: "CSS", value: 20}
    ]
})