export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
  DateTime: any;
};

export type MessageVo = {
  __typename?: 'MessageVO';
  author: UserVo;
  id: Scalars['ID'];
  text: Scalars['String'];
  timestamp: Scalars['DateTime'];
};

export type Mutation = {
  __typename?: 'Mutation';
  /** Creates a new chat room. */
  createRoom: RoomVo;
  /** Creates a new user. */
  createUser: UserVo;
  /** Join the chat room as the authenticated user. */
  joinRoom: RoomVo;
  /** Sends a new message to the chat room. */
  sendMessage: RoomVo;
};


export type MutationCreateRoomArgs = {
  name: Scalars['String'];
};


export type MutationCreateUserArgs = {
  username: Scalars['String'];
};


export type MutationJoinRoomArgs = {
  roomId: Scalars['ID'];
};


export type MutationSendMessageArgs = {
  roomId: Scalars['ID'];
  text: Scalars['String'];
};

export type Query = {
  __typename?: 'Query';
  /** Returns chat room details. */
  getRoom: RoomVo;
  /** Returns all chat rooms, optionally filtered by `name` argument. */
  listRooms: Array<RoomVo>;
};


export type QueryGetRoomArgs = {
  id: Scalars['ID'];
};


export type QueryListRoomsArgs = {
  name?: InputMaybe<Scalars['String']>;
};

export type RoomVo = {
  __typename?: 'RoomVO';
  /** User who manages this chat room */
  administrator: UserVo;
  id: Scalars['ID'];
  /** Messages sent to this chat room */
  messages: Array<MessageVo>;
  /** Chat room name */
  name: Scalars['String'];
  /** Users who joined this chat room */
  users: Array<UserVo>;
};

export type Subscription = {
  __typename?: 'Subscription';
  /** Streams new messages as they are sent to the chat room. */
  streamRoomMessages: MessageVo;
};


export type SubscriptionStreamRoomMessagesArgs = {
  roomId: Scalars['ID'];
};

export type UserVo = {
  __typename?: 'UserVO';
  id: Scalars['ID'];
  /** User's unique username */
  username: Scalars['String'];
};
