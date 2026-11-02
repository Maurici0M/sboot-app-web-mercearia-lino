/*https://pt.stackoverflow.com/questions/342487/como-recuperar-os-valores-de-um-formul%C3%A1rio-em-html-para-uma-fun%C3%A7%C3%A3o-em-javascript*/

const nomeProduto = document.querySelector("#validationCustom01").value;

const categoriaProduto = document.querySelector("#validationCustom02").value;

const unidadeMedida = document.querySelector("#validationCustom03").value;

const fornecedor = document.querySelector("#validationCustom04").value;

const quantidade = document.querySelector("#validationCustom08").value;

const valorUnitario = document.querySelector("#validationCustom09").value;

const dadosProduto = {
    nome: nomeProduto,
    fornecedor: {
      nome: fornecedor,
      telefone: null,
      site: null
    },
    precoUnitario: valorUnitario,
    quantidadeEstoque: quantidade,
    unidadeMedida: unidadeMedida
}



