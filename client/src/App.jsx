import { useState } from 'react'
import './App.css'
import AppRouter from './components/AppRouter'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div style={{height: '100vh'}}>
      <AppRouter />
    </ div>
  )
}

export default App
