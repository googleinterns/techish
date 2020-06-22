// function onLoad() {
//   loadHome();
// }
window.onload = () => {
    loadHome();
  }
  
  async function loadHome() {
    const logging = document.getElementById('logging');
    const link = document.getElementById('login-link');
  
    const logStatus = await getLogStatus();
  
    if (link && logging) {
      if (logStatus.Bool === 'true') {
        link.setAttribute('href', logStatus.Url);
        link.innerHTML = "Logout";
        logging.style.display = 'block';
      } else {
        link.setAttribute('href', logStatus.Url);
        link.innerHTML = "Login";
        logging.style.display = 'block';
      }
    }
  }
  
  async function getLogStatus() {
    const response = await fetch('/userapi');
    const isLoggedIn = await response.json();
    return isLoggedIn;
  }