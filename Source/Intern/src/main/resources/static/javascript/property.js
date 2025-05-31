// Global variables
let currentPage = 1;
const propertiesPerPage = 10;
const prevBtn = document.getElementById('prev-btn');
const nextBtn = document.getElementById('next-btn');
const propertyCardsContainer = document.getElementById('property-cards');

// Fetch and display properties for a specific page
function loadProperties(page) {
    fetch(`/properties?page=${page}&size=${propertiesPerPage}`)
        .then(response => response.json())
        .then(data => {
            const properties = data.content;
            propertyCardsContainer.innerHTML = '';
            properties.forEach(property => {
                const propertyCard = createPropertyCard(property);
                propertyCardsContainer.appendChild(propertyCard);
            });

            // Toggle pagination buttons
            prevBtn.disabled = page === 1;
            nextBtn.disabled = !data.hasNext;
        })
        .catch(error => console.error('Error loading properties:', error));
}

// Create a property card element
function createPropertyCard(property) {
    const card = document.createElement('div');
    card.classList.add('card');

    const image = document.createElement('img');
    image.src = property.imageUrl || '/path/to/default/image.jpg';
    card.appendChild(image);

    const cardInfo = document.createElement('div');
    cardInfo.classList.add('card-info');

    const title = document.createElement('h3');
    title.textContent = property.title;
    cardInfo.appendChild(title);

    const description = document.createElement('p');
    description.textContent = property.description.substring(0, 100) + '...'; // Truncate description
    cardInfo.appendChild(description);

    const btn = document.createElement('a');
    btn.href = `/properties/property-details/${property.id}`;
    btn.classList.add('btn');
    btn.textContent = 'View Details';
    cardInfo.appendChild(btn);

    card.appendChild(cardInfo);

    return card;
}

// Event listeners for pagination buttons
prevBtn.addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        loadProperties(currentPage);
    }
});

nextBtn.addEventListener('click', () => {
    currentPage++;
    loadProperties(currentPage);
});

// Initial load
loadProperties(currentPage);
