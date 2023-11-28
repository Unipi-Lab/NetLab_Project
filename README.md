Il progetto consiste nella implementazione di WORDLE, un gioco di parole web-based, divenuto virale alla fine del 2021.  Il gioco consiste nel trovare una parola inglese formata da 5 lettere, impiegando un numero massimo di 6 tentativi. WORDLE dispone di un vocabolario di parole di 5 lettere, da cui estrae casualmente una parola SW (Secret Word), che gli utenti devono indovinare. Ogni giorno viene selezionata una nuova SW, che rimane invariata fino al giorno successivo e che viene proposta a tutti gli utenti che si collegano al sistema durante quel giorno. Quindi esiste una sola parola per ogni giorno e tutti gli utenti devono indovinarla, questo attribuisce al gioco un aspetto sociale. L’utente propone una parola GW (Guessed Word) e il sistema inizialmente verifica se la parola è presente nel vocabolario. In caso negativo avverte l’utente che deve immettere un’altra parola. In caso la parola sia presente, il sistema fornisce all’utente alcuni indizi, utili per indovinare la parola.


[Scelte implementative](Relazione_Laboratorio.pdf)
<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/GiovanniBraconi/MarsRover">
    <img src="images/wordle.jpg" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Wordle</h3>

  <p align="center">
    Java implementation of the game WORDLE.
    <br />
    <a href="https://github.com/GiovanniBraconi/MarsRover"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

The project consists of the implementation of WORDLE, a web-based game of words, which went wildly viral all over the world in 2021. The game consists of finding an English word of 5 letters with a maximum number of 6 tentatives. WORDLE has a vocabulary with 5-letter words, from which it extracts a SW (Secret Word) that users have to guess. Every day a new SW is selected and proposed to all the users connected to the system, and it remains until the next day. In other words, only a word exists every day, and all the users have to guess it; this gives the game a social aspect. The user proposes a GW (Guessed Word) and the system, initially, verifies if the word is present in the vocabulary. If it's not, it requests a user another word. If, instead, the word is present, the system provides the user with suggestions, useful for guessing the word.

[Requirements](Requirements.pdf)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* [![java][java.com]][java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->

## Getting Started

To run the program follow the instructions below.

### Prerequisites

* Install
  [JDK](https://www.oracle.com/java/technologies/downloads/)

### Installation

* Clone the repo
   ```sh
   git clone https://github.com/Unipi-Lab/NetLab_Project
   ```

### Execution

* After moving inside the project folder run the server.jar file
   ```sh
   java -jar server.jar
   ```
  * Then, on another terminal window, run the client.jar file
   ```sh
   java -jar client.jar
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->

## Usage

After running the client the user can send username and password to register/login. 

    Server: Please insert username password to register
    Client: Joe 1234
    Server: Player registered. Now log in
    Client: Joe 1234
    Server: Player logged in

After loggin in the user can choose one of the following commands:

   ```sh
   playWORDLE: request to participate to the daily session. 
   logout(username): logout from the system.
   sendWord: allow the player to send a word to the system. 
   sendMeStatistics: request the stats of the player.
   share: publish the stats of the player on a social group. 
   showMeSharing: request the stats of the players in the social group.
   ```

An example of a play session is shown below:

   ```
  Client: playWORDLE
  Server: You are playing WORDLE
  Client: sendWord
  Server: Type a word
  Client: aberdonian
  Server: ?X?XX?X?XX
  Client: sendWord
  Server: Type a word
  Client: osmeometric
  Server: +++++++++++ YOU WON
  Client: logout
  Server: Type your username to logout
  Client: john
  Server: You have been logged out
   ```
  


<p align="right">(<a href="#readme-top">back to top</a>)</p>






<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

Your Name - giov.braconi@gmail.com

Project Link: [https://github.com/Unipi-Lab/NetLab_Project](https://github.com/GiovanniBraconi/MarsRover)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

****

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge

[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge

[forks-url]: https://github.com/github_username/repo_name/network/members

[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge

[stars-url]: https://github.com/github_username/repo_name/stargazers

[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge

[issues-url]: https://github.com/github_username/repo_name/issues

[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge

[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/giovanni-braconi/

[product-screenshot]: images/screenshot.png

[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white

[Next-url]: https://nextjs.org/

[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB

[React-url]: https://reactjs.org/

[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D

[Vue-url]: https://vuejs.org/

[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white

[Angular-url]: https://angular.io/

[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00

[Svelte-url]: https://svelte.dev/

[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white

[Laravel-url]: https://laravel.com

[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white

[Bootstrap-url]: https://getbootstrap.com

[dotnet.com]: https://img.shields.io/badge/.NET-0769AD?style=for-the-badge&logo=C-Sharp&logoColor=white

[dotnet-url]: https://dotnet.microsoft.com/en-us/

[java.com]: https://img.shields.io/badge/Java-0769AD?style=for-the-badge&logo=openjdk&logoColor=white

[java-url]: https://www.java.com/en/
