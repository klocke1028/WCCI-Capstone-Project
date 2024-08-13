import { checkAndUpdatePrices } from "./CheckPrices";

const WishlistPriceUpdater = () => {
  const interval = setInterval(() => {
    checkAndUpdatePrices();
  }, 60000);

  return () => clearInterval(interval);
};

export default WishlistPriceUpdater;
