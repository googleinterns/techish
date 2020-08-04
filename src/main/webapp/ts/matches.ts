type authInfoFromServlet = {
    loginUrl: string;
    logoutUrl: string;
    hasProfile: boolean;
};

window.onload = () => {
    loadMatches();
}

async function loadMatches() {
    const logStatus = await logStatusMethod();

    fetch('/matches')
        .then(response => response.json())
        .then((matches) => {
            if ((logStatus.loginUrl === "") && (matches != null)) {
                const matchListElement = document.getElementById('match-history')!;
                matchListElement.innerHTML = "";

                matches.forEach((match: User) => {
                    if(match != null) {
                        matchListElement.appendChild(createMatchElement(match));
                    }
                })
            } else {
                //redirect to log in page from servlet because user is not logged in
                document.location.href = "/index.html#learn-more";
                alert("This feature works for signed-in users. Click here to learn more.");
            }
        });
}

async function logStatusMethod(): Promise<authInfoFromServlet> {
    const response = await fetch('/userapi');
    const currentStatus = await response.json();
    let authStatus: authInfoFromServlet = { loginUrl: currentStatus.LogInUrl, logoutUrl: currentStatus.LogOutUrl, hasProfile: currentStatus.HasProfile };
    return authStatus;
}

function createMatchElement(match: User) {
    const matchElement = document.createElement('li');
    matchElement.className = 'list-group-item';

    const emailElement = document.createElement("a");
    if(match.email != null) {
        emailElement.innerText = match.email;
        emailElement.href = "mailto:" + match.email;
        emailElement.target = "_blank";
    }

    const careerElement = document.createElement('i');
    careerElement.innerText = "\n" + match.occupation + " at " + match.company;

    const allSpecialtiesElement = document.createElement('p');
    allSpecialtiesElement.innerText = "Specialties: ";

    let specialties: string[] = match.specialties;
    for (let index = 0; index < specialties.length; index++) {
        const specialtyElement = document.createElement('span');
        specialtyElement.className = "tags";
        specialtyElement.innerText = specialties[index];
        allSpecialtiesElement.appendChild(specialtyElement);
    }

    const bioElement = document.createElement('p');
    bioElement.innerText = match.userBio;

    const nameElement = document.createElement("a");
    nameElement.innerText = match.name + ": ";
    nameElement.title = match.name;
    nameElement.appendChild(emailElement);
    nameElement.appendChild(careerElement);
    nameElement.appendChild(allSpecialtiesElement);
    nameElement.appendChild(bioElement);

    matchElement.appendChild(nameElement);
    return matchElement;
}

function matchToString(match: User) {
    let toReturn: string = "";
    toReturn += match.name;
    toReturn += " (" + match.specialties + "): ";
    toReturn += match.userBio;
    return toReturn;
}

function sendMatchRequest() {
    const specialty = (<HTMLInputElement>document.getElementById('specialty')).value;

    // Create the request to send to the server using the data we collected from
    // the web form.
    const matchRequest = new MatchRequest(specialty);

    queryServer(matchRequest).then((matches) => {
        if (matches.length == 0) {
            $('#noNewMatchModal').modal();
        } else {
            $('#newMatchModal').modal();
            displayNewMatchPopup(matches);
        }
    });
}

function displayNewMatchPopup(matches: Array<User>) {
    const newMatchContainer = <HTMLSelectElement>document.getElementById('new-matches');

    // clear out any old results
    newMatchContainer.innerHTML = '';

    // add results to the page
    for (const match of matches) {
        const matchString: string = matchToString(match);
        const jsonString: string = JSON.stringify(match);
        let newOption = new Option(matchString, jsonString);
        newMatchContainer.add(newOption, undefined);
    }
}

/**
 * Sends the match request to the server and get back the matches.
 */
async function queryServer(matchRequest: MatchRequest) {
    const json = JSON.stringify(matchRequest);
    return fetch('/new-matches-query', { method: 'POST', body: json, redirect: "follow" })
        .then((response) => {
            if(response.redirected) {
                document.location.href = "/error.html";
            }
            return response.json();
        })
        .then((users) => {
    
            //convert range from json to User
            const out: Array<User> = [];
            users.forEach((range: User) => {
                out.push(range);
            });
            return out;
        });
}

class MatchRequest {
    specialty: string;
    constructor(specialty: string) {
        this.specialty = specialty;
    }
}

class User {
    name: string;
    email: string;
    specialties: string[];
    userBio: string;
    occupation: string;
    company: string;
    constructor(name: string, email: string, specialties: string[], userBio: string, title: string, company: string) {
        this.name = name;
        this.email = email;
        this.specialties = specialties;
        this.userBio = userBio;
        this.occupation = title;
        this.company = company;
    }
}
