async function getProfile() {
    const response = await fetch('/profile');
    const data = await response.json();
    console.log(data);
}

window.onload = () => {
    getProfile();
} 