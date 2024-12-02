import React, { useState, useEffect } from 'react';
import Editor from './components/Editor';
import io from 'socket.io-client';
import './App.css';

const socket = io('http://localhost:8080'); // Replace with your Collaboration Service URL

const App = () => {
  const [code, setCode] = useState('');

  useEffect(() => {
    socket.on('code-change', (newCode) => {
      setCode(newCode);
    });

    return () => {
      socket.off('code-change');
    };
  }, []);

  const handleCodeChange = (newCode) => {
    setCode(newCode);
    socket.emit('code-change', newCode);
  };

  return (
    <div className="App">
      <Editor code={code} onChange={handleCodeChange} />
    </div>
  );
};

export default App;
