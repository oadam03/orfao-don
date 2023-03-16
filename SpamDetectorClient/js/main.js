// TODO: onload function should retrieve the data needed to populate the UI
function populate_table(data){
  console.log(data[0].spamProbability)
  for(i in data){
    let table = document.getElementById("chart")
    let row = table.insertRow(1)
    let filenamecell = row.insertCell(0)
    let spamprobcell = row.insertCell(1)
    let classcell = row.insertCell(2)
    filenamecell.innerHTML = data[i].filename
    spamprobcell.innerHTML = data[i].spamProbability
    classcell.innerHTML = data[i].actualClass
  }
}

function addAccuracy(accuracy){
  let accuracycell = document.getElementById("accuracy");
  accuracycell.innerHTML = accuracy
}

function addPrecision(precision){
  let precisioncell = document.getElementById("precision");
  precisioncell.innerHTML = precision
}

function getPrecision(callURL){
  return fetch(callURL, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    },
  })
    .then(response => response.json())
    .then(response => addPrecision(response))
}

function getAccuracy(callURL){
  return fetch(callURL, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    },
  })
    .then(response => response.json())
    .then(response => addAccuracy(response))
}

/**
 * Function makes a HTTP request to an API
 * **/
function requestData(callURL){
  return fetch(callURL, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    },
  })
    .then(response => response.json())
    .then(response => populate_table(response))
}

let apiURL = "http://localhost:8080/spamDetector-1.0/api/spam";

(function () {

  // your page initialization code here
  // the DOM will be available here
  // console.log(customerData);
  // addDataToTable("customers");

  requestData(apiURL)
    .catch((err) => {
      console.log("Error: " + err)
    })

  getPrecision(apiURL + "/precision")
    .catch((err) => {
      console.log(err)
    })

  getAccuracy(apiURL + "/accuracy")
    .catch((err) => {
      console.log(err)
    })

})();
