import { fetchLoggedInUsersWishlistedGames } from "./LoggedInUserData";
import { fetchBestPrice } from "./ItadData";

export const checkAndUpdatePrices = async () => {
  const wishlistedGames = await fetchLoggedInUsersWishlistedGames();

  wishlistedGames.forEach(async (wishlistedGame) => {
    const itadId = wishlistedGame.itadId;
    const wishlistedGameTitle = wishlistedGame.title;
    const response = await fetchBestPrice({ itadId });

    console.log(wishlistedGameTitle);

    const bestPrice = response;

    const displayedPrice = document.querySelector(
      `#game-price-${itadId}`
    ).innerText;

    // eslint-disable-next-line eqeqeq
    if (bestPrice != displayedPrice) {
      document.querySelector(`#game-price-${itadId}`).innerText = bestPrice;

      window.alert(
        `${wishlistedGameTitle} has a new price! Go to it's page to check it out now!`
      );
    }

    window.alert(
      `${wishlistedGameTitle} does not have a new price. Still tracking, stay tuned!`
    );
  });
};
