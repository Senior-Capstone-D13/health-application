import logo from './logo.svg';
import './App.css';
import React ,{ useState } from 'react';

function App() {
  //obtain info from the backend
  const[numsteps,setnumsteps] = useState('')
  const[distance,setdistance] = useState('')
    useEffect(()=>{
      fetch('http://localhost:5000/distance',{
        'methods':'GET',
        headers : {
          'Content-Type':'application/json'
        }
      })
      .then(response => response.json())
      .then(response => setdistance(response))
      .catch(error => console.log(error))
  
    },[])
    useEffect(()=>{
      fetch('http://localhost:5000/numsteps',{
        'methods':'GET',
        headers : {
          'Content-Type':'application/json'
        }
      })
      .then(response => response.json())
      .then(response => setnumsteps(response))
      .catch(error => console.log(error))
  
    },[])
  return (
    <div className="App">
     <form >
      <input type="text" disabled value = {numsteps}/>
      <input type= "text" disabled value = {distance}/>
     </form>
    </div>
  );
}

export default App;
