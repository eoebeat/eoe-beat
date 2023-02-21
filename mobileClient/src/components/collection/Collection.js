import { View, Text } from 'react-native'
import React from 'react'
import Animated from 'react-native-reanimated'

const { Value } = Animated
const Collection = ({ route }) => {
  const { type, singer } = route.params

  const y = new Value(0)
  return (
    <View>
      <Text>Collection</Text>
    </View>
  )
}

export default Collection
