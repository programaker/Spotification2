package spotification2.common

import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.boolean.Not
import eu.timepit.refined.collection.MaxSize
import eu.timepit.refined.collection.MinSize
import eu.timepit.refined.numeric.NonNegative
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.string.HexStringSpec
import eu.timepit.refined.string.IPv4
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.string.Trimmed
import eu.timepit.refined.string.Uri

type NonBlankStringP = MinSize[1] And Not[MatchesRegex["""^\s+$"""]] And Trimmed
type NonBlankString = String Refined NonBlankStringP

// Size[N] refinement does not work for Strings,
// but MinSize[N] and MaxSize[N] do somehow =S
type StringLength[N] = MinSize[N] And MaxSize[N]

type HexString32P = StringLength[32] And HexStringSpec
type HexString32 = String Refined HexString32P

type UriStringP = Uri
type UriString = String Refined UriStringP

type PositiveIntP = Positive
type PositiveInt = Int Refined PositiveIntP

type NonNegativeIntP = NonNegative
type NonNegativeInt = Int Refined NonNegativeIntP

type HostP = IPv4
type Host = String Refined HostP

// Some Spotify API's allow to select which fields we want to get
// through a String like this one for playlist's tracks: "next,total,items.track.album(id,album_type)"
type FieldsToReturnP = MatchesRegex["""^[a-z]([a-z_.()])+(\,([a-z_.()])+)*[a-z)]$"""]
type FieldsToReturn = String Refined FieldsToReturnP

// The base-62 identifier that you can find at the end of
// the Spotify URI for an artist, track, album, playlist, etc
type SpotifyIdP = MatchesRegex["^[0-9a-zA-Z]+$"]
type SpotifyId = String Refined SpotifyIdP
