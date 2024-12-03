document.getElementById("sendButton").addEventListener("click", async () => {
  const inputText = document.getElementById("inputText").value;

  try {
    const response = await fetch("http://localhost:8080/api/nlp/process", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text: inputText }),
    });

    const result = await response.json();
    document.getElementById("response").innerHTML = `
          <p><strong>Nouns:</strong> ${result.nouns.join(", ")}</p>
          <p><strong>Verbs:</strong> ${result.verbs.join(", ")}</p>
        `;
  } catch (error) {
    document.getElementById(
      "response"
    ).innerHTML = `<p style="color:red;">Error: ${error.message}</p>`;
  }
});
