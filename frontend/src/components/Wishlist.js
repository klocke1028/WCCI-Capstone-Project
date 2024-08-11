function Wishlist({ wishlistedGames }) {
  return (
    <div className="wishlist-container">
      <ul className="game-wishlist">
        {wishlistedGames.map((wishlistedGame) => (
          <li key={wishlistedGame.id}>{wishlistedGame}.text</li>
        ))}
      </ul>
    </div>
  );
}

export default Wishlist;
