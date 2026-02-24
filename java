async function performSearch(query) {
    try {
        const response = await fetch(`proxy.php/search/repositories?q=${encodeURIComponent(query)}`);
        const data = await response.json();
        
        results.innerHTML = data.items.map(repo => `
            <div class="result-card">
                <h2>${repo.name}</h2>
                <p>${repo.description || 'No description'}</p>
                <p>⭐ Stars: ${repo.stargazers_count}</p>
            </div>
        `).join('');
    } catch (error) {
        console.error('Search error:', error);
        results.innerHTML = '<p>Error fetching results</p>';
    }
}
