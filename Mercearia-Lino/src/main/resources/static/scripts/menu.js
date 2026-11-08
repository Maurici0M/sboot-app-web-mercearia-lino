/*Criando lista de elementos do menu*/
const listMenu = [
  {
    link: "/",
    icon: "boxicons:home-alt-2",
    name: "Início",
  },

  {
    link: "/vendas",
    icon: "at-icons:shopping-cart",
    name: "Vendas",
  },

  {
    link: "/produtos",
    icon: "akar-icons:shipping-box-02",
    name: "Estoque",
  },

  {
    link: "produtos/cadastro",
    icon: "charm:sticky-note",
    name: "Cadastro",
  },

  {
    link: "/faturamento",
    icon: "bi-cash",
    name: "Faturamento",
  },
];

const sidebar = document.querySelector(".sidebar");

const aplication = document.querySelector(".aplication");

sidebar.innerHTML = `
  <!-- Sidebar header -->
      <header class="sidebar-header">
        
        <div class="container-logo">
          <iconify-icon icon="at-icons:shopping-cart"></iconify-icon>
          <span>Mercearia Lino</span>
        </div>

        <button class="sidebar-toggle">
          <span class="material-symbols-rounded"> chevron_left </span>
        </button>
      </header>

      <div class="sidebar-content">
        <!-- Search Form -->
        <!-- <form action="#" class="search-form">
          <span class="material-symbols-rounded"> search </span>
          <input type="search" placeholder="Pesquisar..." required />
        </form> -->

        <!-- Menu List -->

        <ul class="menu-list">
          <!-- Código injetado via JavaScript -->
        </ul>
      </div>

      <!-- Sidebar Footer -->
      <div class="sidebar-footer">
        <button class="theme-toggle">
          <div class="theme-label">
            <span class="material-symbols-rounded"> dark_mode </span>
            <span class="theme-dark"> Modo Escuro </span>
          </div>

          <div class="theme-toggle-track">
            <div class="theme-toggle-indicator"></div>
          </div>
        </button>
      </div>

`;

for (let index = 0; index < listMenu.length; index++) {
  let listTag = document.createElement("li");

  listTag.className = "menu-item";

  listTag.innerHTML = `
    <a href="${listMenu[index].link}" class="menu-link">
      <!--<span class="material-symbols-rounded"> ${listMenu[index].icon} </span> -->

      <iconify-icon icon="${listMenu[index].icon}"></iconify-icon>

      <span class="menu-label"> ${listMenu[index].name} </span>
    </a>
    `;

  let containerList = document.querySelector(".menu-list");

  containerList.appendChild(listTag);
}

const btnSidebarToggle = document.querySelector(".sidebar-toggle");

btnSidebarToggle.addEventListener("click", () => {
  sidebar.classList.toggle("collapsed");

  aplication.classList.toggle("collapsed");
});