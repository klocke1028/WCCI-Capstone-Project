export async function fetchLoggedInUser() {
  const loggedInEmail = localStorage.getItem("loggedInEmail");

  if (loggedInEmail) {
    try {
      const response = await fetch(
        `http://localhost:8080/user?email=${encodeURIComponent(loggedInEmail)}`
      );
      if (!response.ok) {
        throw new Error("Network response was not okay.");
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("There was a problem fetching the logged in user: ", error);
    }
  }
  return null;
}
