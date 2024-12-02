import React, { useState } from "react";
import Editor from "./components/Editor";
import "./App.css";

const App = () => {
  const [code, setCode] = useState("");

  const handleCodeChange = (newCode) => {
    setCode(newCode);
    // Send code changes to the Collaboration Service
  };

  return (
    <div className="App">
      <Editor code={code} onChange={handleCodeChange} />
    </div>
  );
};

export default App;
