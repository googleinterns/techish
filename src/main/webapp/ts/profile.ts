async function getProfile() {
    const response = await fetch('/profile');
    const data = await response.json();
}

window.onload = () => {
    getProfile();
} 