// src/components/Editor.js
import React, { useState, useEffect } from "react";
import { MonacoEditor } from "@monaco-editor/react";

const Editor = ({ onChange, code }) => {
  const handleEditorChange = (value) => {
    onChange(value);
  };

  return (
    <MonacoEditor
      height="90vh"
      language="javascript"
      value={code}
      onChange={handleEditorChange}
    />
  );
};

export default Editor;
