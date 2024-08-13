import { fetchLoggedInUser } from './LoggedInUserFetch';


export const checkAndUpdatePrices = async () => {
    const data = await fetchLoggedInUser();
    const wishlistedGames = data.wishlist.games;

    wishlistedGames.forEach(async (wishlistedGame) => {
        const itadId = wishlistedGame.itadId; //Becomes RequestBody

        const shopIds = ; // import a getItadShopIds() function

        const itadResponse = await fetch(`https://api.isthereanydeal.com/games/prices/v2?country=US&nondeals=true&vouchers=false&shops=${shopIds}&key=7f002b2417b6c356251e81434b37c25a3a28402d`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json", 
            },
            body: JSON.stringify([itadId]),
        });

        if (!itadResponse.ok) {
            console.error("Network response was not ok.");
            return;
        }
        
        const itadData = await itadResponse.json();

        console.log(itadData);

        const bestPrice = itadData.deals[1].amount; //Believe this should return the deals array with shop, then price, etc. and inside price is "amount".

        if (bestPrice !== undefined) {
            const priceWhenAdded = wishlistedGame.priceWhenAdded;

            if (bestPrice !== priceWhenAdded) {
                
                wishlistedGame.bestPrice = bestPrice;

                await fetch(`http://localhost:8080/`, { 
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        itadId: itadId,
                        newPrice: bestPrice,
                    }),
                });

                console.log(`Price for ${wishlistedGame.title} updated to ${bestPrice}`);
            }
        } else {
            console.log(`Could not find a price for ${wishlistedGame.title}`);
        }
    });
};