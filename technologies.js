const technologies = ({
    nodes: [
        {id: "bash"},
        {id: "Linux"},
        {id: "SQL"},
        {id: "Java"},
        {id: "Spring"},
        {id: "Hibernate"},
        {id: "JPA"},
        {id: "HTML"},
        {id: "CSS"}
    ],
    links: [
        {source: "bash", target: "Linux", value: 20},
        {source: "SQL", target: "Linux", value: 7},
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