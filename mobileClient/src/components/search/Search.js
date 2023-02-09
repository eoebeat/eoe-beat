import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { Colors } from '../../styles/Styles'

const Search = () => {
  return (
    <View style={styles.container}>
      <Text>Search</Text>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: Colors.white1,
    flex: 1
  }
})

export default Search
