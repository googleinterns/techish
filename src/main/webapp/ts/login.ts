
interface authInfo {
    loginUrl: string;
    logoutUrl: string;
};

async function loadHome() {
  const logging = document.getElementById('log-status-section');
  const link = document.getElementById('login-link');


    //set up function to set login/logout link based on which string is non empty
  if (link && logging) {
  
    if (logStatus.Bool) {
      link.innerHTML = 'Logout';
      logStatus.loggedOutUrl = logStatus.url;
    } else {
      link.innerHTML = 'Login';
    }
    link.setAttribute('href', logStatus.Url);
    logging.style.display = 'block';
  }
}

async function getLogStatus(currentStatus: authInfo) {
  const response = await fetch('/userapi');
  const isLoggedIn = await response.json();
  return isLoggedIn;
}
const currentStatus;
const logStatus = await getLogStatus(currentStatus);

window.onload = () => {
  loadHome();
}