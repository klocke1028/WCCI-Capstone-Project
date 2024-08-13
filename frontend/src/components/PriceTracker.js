import React from "react";
import { fetchLoggedInUsersWishlist } from "./LoggedInUserData";

async function PriceTracker() {
  const loggedInUsersWishlist = await fetchLoggedInUsersWishlist();
}
