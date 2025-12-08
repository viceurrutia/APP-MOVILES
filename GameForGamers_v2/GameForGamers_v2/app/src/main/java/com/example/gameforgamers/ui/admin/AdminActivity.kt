// ... en onCreate ...

// Configuramos el Adapter
adapter = AdminGameAdapter(
mutableListOf(),
isEditor = isEditor,
onDelete = { game ->
    if (isEditor) deleteGame(game)
}
// Ya no pasamos onIncStock ni onDecStock
)
// ... resto igual