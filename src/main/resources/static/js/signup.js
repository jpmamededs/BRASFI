document.getElementById("signup-form").addEventListener("submit", function (event) {
  event.preventDefault();

  const getMultipleSelect = (id) => {
    return Array.from(document.getElementById(id).selectedOptions).map(opt => opt.value);
  };

  const genero = document.getElementById("genero").value;

  const data = {
    userName: document.getElementById("username").value,
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    midleName: document.getElementById("midleName").value,
    biografia: document.getElementById("biografia").value,
    localizacao: document.getElementById("localizacao").value,
    idade: parseInt(document.getElementById("idade").value) || null,
    photo: document.getElementById("photo").value,
    linkInstagram: document.getElementById("linkInstagram").value,
    linkLinkedin: document.getElementById("linkLinkedin").value,
    linkLattes: document.getElementById("linkLattes").value,
    linkWhatsapp: document.getElementById("linkWhatsapp").value,
    role: document.getElementById("role").value,
    profissao: getMultipleSelect("profissao"),
    temasDeAtuacao: getMultipleSelect("temasDeAtuacao")
  };

  // Só adiciona genero se não estiver vazio
  if (genero !== "") {
    data.genero = genero;
  }


  if (data.password !== document.getElementById("passwordcon").value) {
    alert("As senhas não coincidem!");
    return;
  }

  fetch("/req/signup", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  }).then(response => {
    if (response.ok) {
      alert("Cadastro realizado com sucesso!");
      window.location.href = "/req/login";
    } else {
      alert("Erro no cadastro.");
    }
  });
});
