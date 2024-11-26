# Chat System with SendBird API 

### Authors and Contributors: Herena Li, Jaycee Law, Lavender Lo

## Project Summary 
This project is a chat system that incorporates the SendBird API. It allow users to create 
accounts, manage group chats, send and receive messages, and edit past messages. Additionally, users can 
interact with others in real time through the app’s messaging system. Our goal is to provide a simple, user-friendly 
interface for group messaging and individual communication, with features that enhance user experience. It solves the need for
a user-friendly platform where people can communicate easily and efficiently in real time. This project is useful for anyone
looking for a straightforward way to manage conversations and stay connected.

## Table of Contents
1. [Features](#features)
2. [Installation Instructions](#installation-instructions)
3. [Usage Guide](#usage-guide)
4. [License](#license)
5. [Feedback](#feedback)
6. [Contributions](#contributions)

## Features
Core Features: 
- **User Account Management**: Create, log in, and log out of user accounts.
- **Group Chat Creation**: Create or delete group chats with custom names and member lists.
- **Messaging**: Send, receive, and edit messages within chats. Messages update for all participants when edited.
- **Edit Sent Messages**: Users have the ability to edit messages after sending them.

Integrated API: 
- **Sendbird Chat API**: Handles messaging and chat-related functionality.
  
Example for sending a message in a group chat (insert screenshot/ code below): 

## Installation Instructions
- Clone our repository (https://github.com/herenali/CSC207ProjectRepo.git)
- Navigate to project directory and install dependencies using Maven 
- Run Main.java 
- Ensure the correct API token is set in your configuration file; you can find the token in the Sendbird dashboard. 

## Usage Guide
- Upon starting program, you will be prompted to either sign up or log in using your username and password.
- Once you are logged in, you can create a new chat (either single or group). 
- To send a message, select the group chat and type your message in the input text box. 
- If you want to edit a sent message, select the edit message button and update the content. 

## License 
Creative Commons Legal Code

CC0 1.0 Universal

 CREATIVE COMMONS CORPORATION IS NOT A LAW FIRM AND DOES NOT PROVIDE
 LEGAL SERVICES. DISTRIBUTION OF THIS DOCUMENT DOES NOT CREATE AN
 ATTORNEY-CLIENT RELATIONSHIP. CREATIVE COMMONS PROVIDES THIS
 INFORMATION ON AN "AS-IS" BASIS. CREATIVE COMMONS MAKES NO WARRANTIES
 REGARDING THE USE OF THIS DOCUMENT OR THE INFORMATION OR WORKS
 PROVIDED HEREUNDER, AND DISCLAIMS LIABILITY FOR DAMAGES RESULTING FROM
 THE USE OF THIS DOCUMENT OR THE INFORMATION OR WORKS PROVIDED
 HEREUNDER.
    
The full license is in our repository (https://github.com/herenali/CSC207ProjectRepo/blob/main/LICENSE). 

## Feedback
Please give us feedback through this [Google Form](https://docs.google.com/forms/d/e/1FAIpQLSdOlmT7c9pwKURW5UtzZlsilaJc4CuWSx9LaYJjVjzzRQOrZA/viewform?usp=sf_link)

Rules for Valid Feedback:
   - Constructive: provide suggestions for improvement or change that we can implement
   - Specific: describe the issue or suggestion clearly with enough detail to understand the context
      - if possible, provide screenshots, code snippets, or examples to support your feedback.
   - Relevant: ensure the feedback pertains to the specific project or feature in question

Guidelines when Submitting Feedback:
   - after submitting feedback, you can expect an email acknowledging that we've recieved your Google Form submission 
   - you can expect a response or update after a few weeks, depending on the complexity of the feedback

## Contributions 
To add/modify code:
- Go to our repository (https://github.com/herenali/CSC207ProjectRepo.git)
- Fork the project to create your own copy and implement changes on a seperate branch
- Ensure code is correct, adheres to SOLID principles and Clean Architecture, and has sufficient test cases
- Create a pull request to the main branch so others can review your code

To review other's code:
- Make sure their code is organized, well-documented, and correct
- Add comments and manually resolve conflicts when merging, if necessary
