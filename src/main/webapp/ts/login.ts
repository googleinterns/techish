// interface Value {}
window.onload = () => {
  loadHome();
};

async function loadHome() {
  const logging = document.getElementById('log-status-section');
  const link = document.getElementById('login-link');

  const logStatus = await getLogStatus();
  console.log(logStatus.Bool);

  if (link && logging) {
    link.setAttribute('href', logStatus.Url);
    logging.style.display = 'block';
    if (logStatus.Bool) {
      link.innerHTML = 'Logout';
    } else {
      link.innerHTML = 'Login';
    }
  }
}

async function getLogStatus() {
  const response = await fetch('/userapi');
  const isLoggedIn = await response.json();
  return isLoggedIn;
}
