// Fixed search functionality
const searchInput = document.getElementById('searchInput');
const suggestions = document.getElementById('suggestions');
const results = document.getElementById('results');

// Store suggestions array to avoid recreating event listeners
let currentSuggestions = [];

searchInput.addEventListener('input', async () => {
    const query = searchInput.value.trim();
    if (!query) {
        suggestions.style.display = 'none';
        return;
    }

    // Simulate API call for suggestions
    const mockSuggestions = ['Google', 'Bing', 'DuckDuckGo', 'Yahoo', 'Ask Jeeves'];
    currentSuggestions = mockSuggestions.filter(sug => 
        sug.toLowerCase().includes(query.toLowerCase())
    );
    
    if (currentSuggestions.length > 0) {
        suggestions.innerHTML = currentSuggestions.map(sug => `<div data-value="${sug}">${sug}</div>`).join('');
        suggestions.style.display = 'block';
    } else {
        suggestions.style.display = 'none';
    }
});

// Handle suggestion clicks (outside the input event listener)
suggestions.addEventListener('click', (event) => {
    if (event.target.tagName === 'DIV') {
        searchInput.value = event.target.getAttribute('data-value');
        suggestions.style.display = 'none';
        performSearch(searchInput.value);
    }
});

// Close suggestions when clicking outside
document.addEventListener('click', (event) => {
    if (event.target !== searchInput) {
        suggestions.style.display = 'none';
    }
});

async function performSearch(query) {
    // Show loading state
    results.innerHTML = '<div class="result-card">Searching...</div>';
    
    try {
        // Using GitHub's public API that supports CORS
        const response = await fetch(`https://api.github.com/search/repositories?q=${encodeURIComponent(query)}`);
        const data = await response.json();
        
        if (data.items && data.items.length > 0) {
            results.innerHTML = data.items.map(repo => `
                <div class="result-card">
                    <h2>${repo.name}</h2>
                    <p>${repo.description || 'No description'}</p>
                    <p>⭐ Stars: ${repo.stargazers_count}</p>
                    <p>🔗 <a href="${repo.html_url}" target="_blank">View on GitHub</a></p>
                </div>
            `).join('');
        } else {
            results.innerHTML = '<div class="result-card">No repositories found for this search.</div>';
        }
    } catch (error) {
        console.error('Search error:', error);
        results.innerHTML = '<div class="result-card">Error fetching results. Please try again.</div>';
    }
}

// Add search on Enter key
searchInput.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        suggestions.style.display = 'none';
        performSearch(searchInput.value);
    }
});
