# Spam Email Detector Model
***
### Overview
Our project uses a bag of words model in order to determine whether an email is spam or not, based on the contained words. It checks each word for its corresponding frequency within a training dataset of spam/ham files, and uses this data in order to generate
a probability value for each word. Applying this to all the words in a given file/email, we get a somewhat accurate idea of whether and email is spam or not. We then calculate the accuracy of our program by using our predicted probability values.

### Tweaks / Additions
In order for a more accurate sense of what is spam/ham, we decided not to make the words uniform. Spam tends to have a sense of urgency attached to the message, and this can be portrayed using words in CAPS LOCK. By intentionally avoiding this, we can more
accurately determine whether an email is spam/ham.
***
## SETUP
Requires GlassFish (or alternative program that can run a java application server)
<br>
<a href="https://projects.eclipse.org/projects/ee4j.glassfish">Download GlassFish</a>
### API SETUP
Open `.\spamDetectorServer\ `

Set up GlassFish (or alternative), required artifacts are included in the project. Start server.

### FRONTEND SETUP
Open `.\spamDetectorClient\index.html ` 