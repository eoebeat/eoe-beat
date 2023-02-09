import { configureStore } from '@reduxjs/toolkit'
import playerReducer from '../components/player/playerSlice'

export const store = configureStore({
  reducer: {
    player: playerReducer
  }
})
