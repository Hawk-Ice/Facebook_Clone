const functions = require('firebase-functions');
const firebase_tools = require('firebase-tools');
var admin = require('firebase-admin');
const db = admin.firestore();



exports.listenToNewComments = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Comments/{commentID}') 
  .onCreate((snap, context) => {
    
    var newComment = snap.data();

    db.doc('PostsCollection/'+context.params.postID).update({
      latestComment : {
        commenter: newComment.commenter,
        commentText: newComment.commentText,
        commenterProfilePicture: newComment.commenterProfilePicture
      }
    });

    db.doc('PostsCollection/'+context.params.postID).update({
      numberOfComments : admin.firestore.FieldValue.increment(1)
    });

    return null;
  });

exports.listenToDeletedComments = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Comments/{commentID}') 
  .onDelete((snap, context) => {
    
    // var deletedReact = snap.data();

    db.doc('PostsCollection/'+context.params.postID).update({
      numberOfComments : admin.firestore.FieldValue.increment(-1)
    });

    return null;
  });

exports.listenToUpdatedComments = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Comments/{commentID}')
  .onUpdate((change, context) => {
    // -- document changed
    var updatedComment = change.after.data();

    return db.doc('PostsCollection/'+context.params.postID).update({
      latestComment : {
        commenter: updatedComment.commenter,
        commentText: updatedComment.commentText,
        commenterProfilePicture: updatedComment.commenterProfilePicture
      }
    });
});