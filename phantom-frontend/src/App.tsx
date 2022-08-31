import React, { useEffect, useState } from 'react';
import * as utils from './Utils';


const App = () => {
  const [data, setData] = useState([]);

    useEffect(() => {
        const source = utils.buildEventSource(`http://localhost:8080/events`);
    
        source.addEventListener('open', () => {
          console.log('SSE opened!');
        });
    
        source.addEventListener('message', (e) => {
          console.log(e.data);
          setData((data) => data.concat(e.data));
        });
    
        source.addEventListener('error', (e) => {
          console.error('Error: ',  e);
        });
    
        return () => {
          source.close();
        };
      }, []);
    
      return (
        
        <div style={{ padding: "0 20px" }}>
        <div>
          <h4>Decision Taken List {data.length}</h4>
          {data.map((item, index) => (
            <div key={index}>{index + 1} - {item}</div>
          ))}
        </div>
      </div>

      );
}

export default App;