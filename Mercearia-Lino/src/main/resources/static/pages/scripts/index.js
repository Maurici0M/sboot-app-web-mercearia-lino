

/**/
let data = new Date();

const copyright = document.querySelector(".copyright");

copyright.innerHTML = `
    <p>Feito com &hearts; no 3º Semestre de Sistemas de Informação</p>
    <p>&COPY; 2026 - ${data.getFullYear()}</p>
    `;

const initialMessage = document.querySelector(
  ".aplication>.container-content>h1",
);

let typeDay = "";

if (data.getHours() > -1 && data.getHours() < 13) {
  typeDay = "Bom dia! 🌞";
} else if (data.getHours() > 12 && data.getHours() < 18) {
  typeDay = "Boa tarde ⛅";
} else {
  typeDay = "Boa noite! 🌙";
}

initialMessage.textContent = "Olá, José! " + typeDay;
