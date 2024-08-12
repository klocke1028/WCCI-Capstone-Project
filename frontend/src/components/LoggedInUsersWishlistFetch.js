import { fetchLoggedInUser } from "./LoggedInUserFetch";

export async function fetchLoggedInUsersWishlist() {
  const loggedInUser = await fetchLoggedInUser();

  const loggedInUsersWishlist = loggedInUser.wishlist;

  return loggedInUsersWishlist;
}
