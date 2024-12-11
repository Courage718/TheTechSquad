// document.getElementById("send_btn").addEventListener("click", function () {
//   // Get the user input
//   const userInput = document.getElementById("inputText").value;

//   // Check if input is empty
//   if (!userInput.trim()) {
//     alert("Please enter some text.");
//     return;
//   }

//   // Create the request payload
//   const payload = { text: userInput };

//   // Use fetch to send the data to the backend (you can also use AJAX here)
//   fetch("/process-nlp", {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//     },
//     body: JSON.stringify(payload),
//   })
//     .then((response) => response.json())
//     .then((data) => {
//       // Display the result in the response div
//       document.getElementById(
//         "response"
//       ).innerHTML = `<strong>Processed Task:</strong> ${data.processedTask}<br><strong>Formatted Date:</strong> ${data.date}<br><strong>Category:</strong> ${data.category}`;
//     })
//     .catch((error) => {
//       console.error("Error:", error);
//       document.getElementById("response").innerHTML =
//         "Error processing the request. Please try again later.";
//     });
// });
