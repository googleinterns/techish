type authInfoFromServlet = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

function setForm(value: string) {
    const menteeForm =  document.getElementById('Mentee');
    const mentorForm = document.getElementById('Mentor');
    const userTypeButton = document.getElementById('user-input');

    if(menteeForm && mentorForm && userTypeButton){
        if(value == 'Mentee') {
            menteeForm.setAttribute('style','display:block;');
            mentorForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentee');
            const menteeName = document.getElementById('name-input') as HTMLInputElement;
            const menteeSchool = document.getElementById('school-input') as HTMLInputElement;
            const menteeMajor = document.getElementById('major-input') as HTMLInputElement;
            if(menteeName && menteeSchool && menteeMajor) {
                menteeName.required = true;
                menteeSchool.required = true;
                menteeMajor.required = true;
            }
            else{
                console.error("Required forms failed for mentee form");
            }
        }
        else {
            mentorForm.setAttribute('style','display:block;');
            menteeForm.setAttribute('style','display:none;');
            userTypeButton.setAttribute('value','Mentor');

            const mentorName = document.getElementById('profName-input') as HTMLInputElement;
            const mentorCompany = document.getElementById('company-input') as HTMLInputElement;
            const mentorCareerTitle = document.getElementById('careerTitle-input') as HTMLInputElement;
            const mentorSpecialty = document.getElementById('specialty-input') as HTMLInputElement;
            const mentorBio = document.getElementById('bio-input') as HTMLInputElement;
            if(mentorName && mentorCompany && mentorCareerTitle && mentorSpecialty && mentorBio) {
                mentorName.required = true;
                mentorCompany.required = true;
                mentorCareerTitle.required = true;
                mentorSpecialty.required = true;
                mentorBio.required = true;
            }
            else{
                console.error("Required forms failed for mentor form");
            }
        }
    }
} 

async function redirectIfLoggedOut() {
    const logStatus = await logStatusMethod();
    if(logStatus.loginUrl != "") {
        document.location.href = logStatus.loginUrl;
        alert("Please login or create an account.");
    }
}
async function logStatusMethod(): Promise<authInfoFromServlet> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfoFromServlet = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

window.onload = () => {
    redirectIfLoggedOut();
}
