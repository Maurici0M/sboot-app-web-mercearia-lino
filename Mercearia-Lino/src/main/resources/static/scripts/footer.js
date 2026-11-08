const version = "1.0 Estável";

const listItens = [
    {
        Developer:  [
            {
                text: "Documentação",
                link: "#"
            }
        ],

        Navegacao: [
            {
                text: "Home",
                link: "/"
            },
            
            {
                text: "Vendas",
                link: "/vendas"
            },
            
            {
                text: "Estoque",
                link: "/produtos"
            },
            
            {
                text: "Cadastros",
                link: "produtos/cadastro"
            },
            
            {
                text: "Faturamento",
                link: "#"
            },
        ],

        Social: [
            {
                text: "Cyntia Garçete",
                linkedin: "https://www.linkedin.com/in/cyntialino/",
                github: "#"
            },

            {
                text: "Izabela Almeida",
                linkedin: "https://www.linkedin.com/in/izabelasousa-almeida/",
                github: "#"
            },

            {
                text: "Mauricio Marques",
                linkedin: "https://www.linkedin.com/in/mauricio-marques-costa-994b25210/",
                github: "https://github.com/Maurici0M"
            },
        ]
    }
];

const footer = document.querySelector(".footer");

footer.innerHTML = `
    <div class="container">
        <div class="row">
          <div class="footer-col">
            <h4>Versão atual</h4>
            <ul>
              <li>
                <a rel="noopener noreferrer" style="cursor: pointer"
                  >${version}</a
                >
              </li>
            </ul>
          </div>

          <div class="footer-col col-2">
            <h4>Developer</h4>
            <ul>
                <!-- Injetado via JS -->
            </ul>
          </div>

          <div class="footer-col col-3">
            <h4>Veja também</h4>
            <ul>
                <!-- Injetado via JS -->
            </ul>
          </div>

          <div class="footer-col col-4">
            <h4>Nos Siga!</h4>
            <ul>
                <!-- Injetado via JS -->
            </ul>
          </div>
        </div>

        <div class="copyright"></div>
      </div>
`

const column2 = document.querySelector(".col-2>ul");

for (let index = 0; index < listItens[0].Developer.length; index++) {
    let listOfElements = document.createElement("li");
    
    listOfElements.innerHTML = `
        <a href="${listItens[0].Developer[index].link}" target="_blank" rel="noopener noreferrer">
            ${listItens[0].Developer[index].text}
        </a>
    `

    column2.appendChild(listOfElements);    
}

const column3 = document.querySelector(".col-3>ul");

for (let index = 0; index < listItens[0].Navegacao.length; index++) {
    let listOfElements = document.createElement("li");
    
    listOfElements.innerHTML = `
        <a href="${listItens[0].Navegacao[index].link}" rel="noopener noreferrer">
            ${listItens[0].Navegacao[index].text}
        </a>
    `

    column3.appendChild(listOfElements);    
}

const column4 = document.querySelector(".col-4>ul");

for (let index = 0; index < listItens[0].Social.length; index++) {
    let listOfElements = document.createElement("li");
    
    listOfElements.innerHTML = `
        <span> ${listItens[0].Social[index].text} </span>

            <div class="social-icons">
              <a href="${listItens[0].Social[index].github}" target="_blank" rel="noopener noreferrer">
                <iconify-icon icon="akar-icons:github-fill"></iconify-icon>
              </a>

              <a
                href="${listItens[0].Social[index].linkedin}"
                target="_blank"
                rel="noopener noreferrer"
              >
                <iconify-icon
                  icon="akar-icons:linkedin-fill"
                ></iconify-icon>
              </a>
            </div>
    `

    column4.appendChild(listOfElements);    
}
