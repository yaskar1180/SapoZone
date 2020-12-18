const functions = require("firebase-functions");

const cors = require('cors');

var  stringify  =  require ('json-stringify-safe') ; 

const express = require('express');

const app = express();

app.use(cors())
app.use( express.json())
app.listen(3000, () => {
  console.log(`Serveur de récup commande ok`);
});



//Serv pour accepter commandes
app.post('/connect',(req,res) => {

    const body = req.body;
    const userName = body.userName;
    const password = body.password;

    console.log(body)
    const url = 'mysql-yask1180.alwaysdata.net'  + '/accept_pos_order'
    console.log(url)

  const headers = 
  {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + body.token,
    'Access-Control-Allow-Origin' : '*'
  }

    console.log('eeeeeeeeeeee')


     axios.post(
      url,
      {
        reason: 'accepted.'
      }
      ,{
        headers: headers
      } 
      
    )
    .then((response) => {
     // asynchronously called
     console.log(response)
     res.send(stringify(response));
     return
   })
   .catch(err =>{
       console.log(err);
   });
})


//Serv pour deny commandes
app.post('/denyOrder',(req,res) => {
  res.set('Cache-Control', 'public, max-age = 300, s-maxage=600');
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Access-Control-Allow-Methods', 'GET, PUT, POST, OPTIONS');
  res.set('Access-Control-Allow-Headers', '*');
  res.header("Access-Control-Allow-Headers", "*");

  const body = req.body;
  const idUberEats = body.idUberEats;
  console.log(body)
  const url = 'https://api.uber.com/v1/eats/orders/' + idUberEats + '/deny_pos_order'
  console.log(url)

  const headers = 
  {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + body.token,
    'Access-Control-Allow-Origin' : '*'
  }

    console.log('eeeeeeeeeeee')


     axios.post(
      url,
      {
        reason: 'accepted.'
      }
      ,{
        headers: headers
      } 
      
    )
    .then((response) => {
     // asynchronously called
     console.log(response)
     res.send(stringify(response));
     return
   })
   .catch(err =>{
       console.log(err);
   });
})


//Serv pour se connecter sur Uber Eats
app.post('/connectUE',(req,res) => {
  res.set('Cache-Control', 'public, max-age = 300, s-maxage=600');
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Access-Control-Allow-Methods', 'GET, PUT, POST, OPTIONS');
  res.set('Access-Control-Allow-Headers', '*');
  res.header("Access-Control-Allow-Headers", "*");

  const body = req.body;
  console.log(body)
  const url = 'https://login.uber.com/oauth/v2/token/'
  console.log(url)

  const headers = 
  {
    'Content-Type': 'application/x-www-form-urlencoded'
  }

    console.log('eeeeeeeeeeee')


     axios.post(
      url,
      {
        client_id: body.client_id,
        client_secret: body.client_secret,
        grant_type: 'client_credentials',
        scope: 'eats.store'
        }
      ,{
        headers: headers
      } 
      
    )
    .then((response) => {
     // asynchronously called
     console.log(response)
     res.send(stringify(response));
     return stringify(response)
   })
   .catch(err =>{
       console.log(err);
   });
})


exports.app = functions.https.onRequest (app);

const connectUberEats = express();
connectUberEats.use( express.json())
connectUberEats.listen(4000, () => {
  console.log("Serveur connexion UE à l'ecoute");
});


