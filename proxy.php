<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET');

$url = 'https://api.github.com' . $_SERVER['REQUEST_URI'];
$context = stream_context_create([
    'http' => [
        'header' => 'User-Agent: MyProxyApp'
    ]
]);
echo file_get_contents($url, false, $context);
?>
