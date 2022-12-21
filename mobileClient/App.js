import React, { useEffect, useState } from 'react'
import {
  Button,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View
} from 'react-native'

import { Colors } from 'react-native/Libraries/NewAppScreen'

import TrackPlayer, { useProgress } from 'react-native-track-player'

import UserAgent from 'react-native-user-agent'

const App = () => {
  const isDarkMode = useColorScheme() === 'dark'

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter
  }

  const progress = useProgress()
  const [finishedSetup, setFinishedSetup] = useState(false)

  // The url in track should be updated every 2 hours.
  let track1 = {
    url: 'https://upos-hz-mirrorakam.akamaized.net/upgcxcode/98/39/928823998/928823998_nb3-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1671650232&gen=playurlv2&os=akam&oi=2914256548&trid=3f2d59cb728d43a4a449135801faff19u&mid=0&platform=pc&upsig=10b62780e17c44a0cc7513bf8acee061&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&hdnts=exp=1671650232~hmac=19b8a896c012e35ad7558d01a19c77b8d541459dc55cc04392124bf258dafaf8&bvc=vod&nettype=0&orderid=0,1&buvid=&build=0&agrr=1&bw=21456&logo=80000000',
    title: 'track1',
    artist: '米诺',
    duration: 251,
    headers: {
      accept: '*/*',
      'User-Agent':
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36',
      'accept-encoding': 'gzip, deflate, br',
      'accept-language': 'zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7',
      Connection: 'keep-alive',
      Referer: 'https://www.bilibili.com'
    }
  }

  useEffect(() => {
    const getCurrentUserAgent = async () => {
      try {
        if (!finishedSetup) return
        const currentUserAgent = await UserAgent.getWebViewUserAgent()
        track1.headers = { ...track1.headers, 'User-Agent': currentUserAgent }
        await TrackPlayer.add([track1])
      } catch (e) {
        console.log(e)
      }
    }
    getCurrentUserAgent()
  }, [finishedSetup])

  useEffect(() => {
    const setupPlayerAndLoadTrack = async () => {
      try {
        if (!finishedSetup) {
          await TrackPlayer.setupPlayer()
        }
        setFinishedSetup(true)
      } catch (e) {
        console.log(e)
      }
    }
    setupPlayerAndLoadTrack()
  }, [])

  const onPressPlay = () => {
    if (finishedSetup) {
      TrackPlayer.play()
    }
  }

  const onPressPause = () => {
    if (finishedSetup) {
      TrackPlayer.pause()
    }
  }

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <View style={backgroundStyle}>
        <Text>{`Progress: ${progress.position}`}</Text>
        <Button title="Play" onPress={onPressPlay} />
        <Button title="Pause" onPress={onPressPause} />
      </View>
    </SafeAreaView>
  )
}

const styles = StyleSheet.create({})

export default App
