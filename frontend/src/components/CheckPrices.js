import { fetchLoggedInUsersWishlistedGames } from "./LoggedInUserData";
import { fetchBestPrice } from "./ItadData";

export const checkAndUpdatePrices = async () => {
  const wishlistedGames = await fetchLoggedInUsersWishlistedGames();

  wishlistedGames.forEach(async (wishlistedGame) => {
    const itadId = wishlistedGame.itadId;
    const wishlistedGameTitle = wishlistedGame.title;
    const response = await fetchBestPrice({ itadId });

    const bestPrice = response;

    const displayedPriceElement = document.querySelector(
      `#game-price-${itadId}`
    );
    if (!displayedPriceElement) {
      console.error(`Element with ID #game-price-${itadId} not found.`);
      return;
    }

    const displayedPrice = displayedPriceElement.innerText;

    // eslint-disable-next-line eqeqeq
    if (bestPrice != displayedPrice) {
      displayedPriceElement.innerText = bestPrice;
      // Need an alert system here
    }
  });
};
