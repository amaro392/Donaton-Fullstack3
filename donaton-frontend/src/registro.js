// ── Toggle password visibility ──────────────────────────────────
document.getElementById('togglePw').addEventListener('click', function () {
  const input = document.getElementById('password');
  const isHidden = input.type === 'password';
  input.type = isHidden ? 'text' : 'password';
  document.getElementById('eye-show').style.display = isHidden ? 'none' : 'block';
  document.getElementById('eye-hide').style.display = isHidden ? 'block' : 'none';
});

document.getElementById('toggleConfirm').addEventListener('click', function () {
  const input = document.getElementById('confirm');
  input.type = input.type === 'password' ? 'text' : 'password';
});

// ── Password strength meter ──────────────────────────────────────
document.getElementById('password').addEventListener('input', function () {
  const val = this.value;
  const bar = document.getElementById('pwBar');
  const hint = document.getElementById('pwHint');

  let score = 0;
  if (val.length >= 8) score++;
  if (/[A-Z]/.test(val)) score++;
  if (/[0-9]/.test(val)) score++;
  if (/[^A-Za-z0-9]/.test(val)) score++;

  const levels = [
    { pct: '0%',   color: 'transparent', text: '' },
    { pct: '25%',  color: '#8B2E2E',     text: 'Muy débil' },
    { pct: '50%',  color: '#B8956A',     text: 'Débil' },
    { pct: '75%',  color: '#8B9A3A',     text: 'Buena' },
    { pct: '100%', color: '#3A6234',     text: 'Muy segura ✓' },
  ];

  const level = val.length === 0 ? levels[0] : levels[score] || levels[1];
  bar.style.width = level.pct;
  bar.style.background = level.color;
  hint.textContent = level.text;
  hint.style.color = level.color;
});

// ── Helpers ──────────────────────────────────────────────────────
function setError(groupId, errorId, msg) {
  document.getElementById(groupId).classList.add('has-error');
  document.getElementById(groupId).classList.remove('has-success');
  document.getElementById(errorId).textContent = msg;
}

function setSuccess(groupId, errorId) {
  document.getElementById(groupId).classList.remove('has-error');
  document.getElementById(groupId).classList.add('has-success');
  document.getElementById(errorId).textContent = '';
}

function clearState(groupId, errorId) {
  document.getElementById(groupId).classList.remove('has-error', 'has-success');
  document.getElementById(errorId).textContent = '';
}

// Inline validation on blur
document.getElementById('nombre').addEventListener('blur', () => validateNombre());
document.getElementById('apellido').addEventListener('blur', () => validateApellido());
document.getElementById('email').addEventListener('blur', () => validateEmail());
document.getElementById('password').addEventListener('blur', () => validatePassword());
document.getElementById('confirm').addEventListener('blur', () => validateConfirm());

function validateNombre() {
  const val = document.getElementById('nombre').value.trim();
  if (!val) { setError('group-nombre', 'error-nombre', 'El nombre es obligatorio.'); return false; }
  if (val.length < 2) { setError('group-nombre', 'error-nombre', 'Ingresa al menos 2 caracteres.'); return false; }
  setSuccess('group-nombre', 'error-nombre');
  return true;
}

function validateApellido() {
  const val = document.getElementById('apellido').value.trim();
  if (!val) { setError('group-apellido', 'error-apellido', 'El apellido es obligatorio.'); return false; }
  if (val.length < 2) { setError('group-apellido', 'error-apellido', 'Ingresa al menos 2 caracteres.'); return false; }
  setSuccess('group-apellido', 'error-apellido');
  return true;
}

function validateEmail() {
  const val = document.getElementById('email').value.trim();
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!val) { setError('group-email', 'error-email', 'El correo es obligatorio.'); return false; }
  if (!re.test(val)) { setError('group-email', 'error-email', 'Ingresa un correo válido.'); return false; }
  setSuccess('group-email', 'error-email');
  return true;
}

function validatePassword() {
  const val = document.getElementById('password').value;
  if (!val) { setError('group-password', 'error-password', 'La contraseña es obligatoria.'); return false; }
  if (val.length < 8) { setError('group-password', 'error-password', 'La contraseña debe tener al menos 8 caracteres.'); return false; }
  setSuccess('group-password', 'error-password');
  return true;
}

function validateConfirm() {
  const pw = document.getElementById('password').value;
  const val = document.getElementById('confirm').value;
  if (!val) { setError('group-confirm', 'error-confirm', 'Confirma tu contraseña.'); return false; }
  if (val !== pw) { setError('group-confirm', 'error-confirm', 'Las contraseñas no coinciden.'); return false; }
  setSuccess('group-confirm', 'error-confirm');
  return true;
}

function validateTerms() {
  const checked = document.getElementById('terms').checked;
  if (!checked) {
    document.getElementById('error-terms').textContent = 'Debes aceptar los términos para continuar.';
    return false;
  }
  document.getElementById('error-terms').textContent = '';
  return true;
}

// ── Form submission ──────────────────────────────────────────────
document.getElementById('registerForm').addEventListener('submit', function (e) {
  e.preventDefault();

  const v1 = validateNombre();
  const v2 = validateApellido();
  const v3 = validateEmail();
  const v4 = validatePassword();
  const v5 = validateConfirm();
  const v6 = validateTerms();

  if (!v1 || !v2 || !v3 || !v4 || !v5 || !v6) return;

  // Simulate async registration
  const btn = document.getElementById('btnSubmit');
  const btnText = document.getElementById('btnText');
  const btnLoader = document.getElementById('btnLoader');
  btn.disabled = true;
  btnText.style.display = 'none';
  btnLoader.style.display = 'flex';

  setTimeout(() => {
    const nombre = document.getElementById('nombre').value.trim();

    // Store user data in sessionStorage for use in other pages
    sessionStorage.setItem('usuario', JSON.stringify({
      nombre: nombre,
      apellido: document.getElementById('apellido').value.trim(),
      email: document.getElementById('email').value.trim(),
    }));

    // Show success state
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('successName').textContent = nombre;
    document.getElementById('successState').style.display = 'block';
  }, 1400);
});