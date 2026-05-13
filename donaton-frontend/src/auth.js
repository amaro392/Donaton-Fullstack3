
const ALLOWED_DOMAINS = ["@gmail.com", "@hotmail.com", "@outlook.com"];

const form = document.querySelector("form");
const emailInput = document.getElementById("email");
const passwordInput = document.getElementById("password");

function getEmailError(email) {
  if (!email) return "Ingresa tu correo electrónico.";

  const hasAt = email.includes("@");
  if (!hasAt) return "El correo no es válido.";

  const domain = "@" + email.split("@")[1];
  const allowed = ALLOWED_DOMAINS.some(d => domain.toLowerCase() === d);
  if (!allowed) return `Solo se permiten correos de: ${ALLOWED_DOMAINS.join(", ")}`;

  return null;
}


function validatePassword(password) {
  if (!password) {
    showError(passwordInput, "Ingresa tu contraseña.");
    return false;
  }
  if (password.length < 3) {
    showError(passwordInput, "La contraseña debe tener al menos 3 caracteres");
    return false;
  }
  return true;
}

function showError(input, message) {
  clearError(input);
  input.style.borderColor = "#c0392b";
  const err = document.createElement("span");
  err.className = "field-error";
  err.style.cssText = "display:block; font-size:0.78rem; color:#c0392b; margin-top:5px;";
  err.textContent = message;
  input.closest(".input-wrap").after(err);
}

function clearError(input) {
  input.style.borderColor = "";
  const prev = input.closest(".form-group").querySelector(".field-error");
  if (prev) prev.remove();
}

emailInput.addEventListener("blur", () => {
  const error = getEmailError(emailInput.value.trim());
  if (error) showError(emailInput, error);
  else clearError(emailInput);
});

emailInput.addEventListener("input", () => clearError(emailInput));

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const email = emailInput.value.trim();
  const password = passwordInput.value;

  const emailError = getEmailError(email);
  if (emailError) {
    showError(emailInput, emailError);
    return;
  }

  if (!validatePassword(password)) return;

  console.log("Acceso permitido para:", email);
  alert(`Bienvenido, ${email}`);
});

export {};