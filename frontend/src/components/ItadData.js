export async function fetchShopIds() {
  try {
    const response = await fetch(`http://localhost:8080/price-alerts/shop-ids`);
    if (!response.ok) {
      throw new Error("Network response was not okay.");
    }

    const responseText = await response.text();

    return responseText;
  } catch (error) {
    console.error("There was a problem fetching the ITAD shop IDs: ", error);
  }

  return null;
}

export async function fetchBestPrice({ itadId }) {
  try {
    const url = `http://localhost:8080/games/best-price`;
    const body = itadId;
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });
    if (!response.ok) {
      throw new Error("Network response was not okay.");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error(
      "There was a problem fetching the game's best price: ",
      error
    );
  }

  return null;
}
