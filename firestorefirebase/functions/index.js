const functions = require('firebase-functions');
const firebase_tools = require('firebase-tools');
var admin = require('firebase-admin');
var app = admin.initializeApp();
const db = admin.firestore();

exports.listenToNewComments = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Comments/{commentID}') 
  .onCreate((snap, context) => {
    
    var newComment = snap.data();

    db.doc('PostsCollection/'+context.params.postID).update({
      latestComment : {
        commenter: newComment.commenter,
		  commentText: newComment.commentText
      }
    });

    db.doc('PostsCollection/'+context.params.postID).update({
      numberOfComments : admin.firestore.FieldValue.increment(1)
    });

    return null;
  });

// --- Triggers for Reactions ---------------------------------------
exports.listenToNewReactions = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Reactions/{reactID}') 
  .onCreate((snap, context) => {
    
    var newReact = snap.data();

    if(newReact.reaction === "Like"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Like' : admin.firestore.FieldValue.increment(1)
      });
    }else if(newReact.reaction === "Love"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Love' : admin.firestore.FieldValue.increment(1)
      });
    }else if(newReact.reaction === "Haha"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Haha' : admin.firestore.FieldValue.increment(1)
      });
    }else if(newReact.reaction === "Wow"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Wow' : admin.firestore.FieldValue.increment(1)
      });
    }else if(newReact.reaction === "Sad"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Sad' : admin.firestore.FieldValue.increment(1)
      });
    }else{
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Angry' : admin.firestore.FieldValue.increment(1)
      });
    }

    db.doc('PostsCollection/'+context.params.postID).update({
      numberOfReactsCounter : admin.firestore.FieldValue.increment(1)
    });

    return null;
  });
// -- Listen to reaction change
exports.listenToUpdatedReactions = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Reactions/{reactID}') 
  .onUpdate((change, context) => {
    
    var updatedReaction = change.after.data();
    var oldReaction = change.before.data();

    if(updatedReaction.reaction !== oldReaction.reaction){
        // -- Old Reaction
      if(oldReaction.reaction === "Like"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Like' : admin.firestore.FieldValue.increment(-1)
        });
      }else if(oldReaction.reaction === "Love"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Love' : admin.firestore.FieldValue.increment(-1)
        });
      }else if(oldReaction.reaction === "Haha"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Haha' : admin.firestore.FieldValue.increment(-1)
        });
      }else if(oldReaction.reaction === "Wow"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Wow' : admin.firestore.FieldValue.increment(-1)
        });
      }else if(oldReaction.reaction === "Sad"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Sad' : admin.firestore.FieldValue.increment(-1)
        });
      }else{
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Angry' : admin.firestore.FieldValue.increment(-1)
        });
      }
      // -- updated Reaction
      if(updatedReaction.reaction === "Like"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Like' : admin.firestore.FieldValue.increment(1)
        });
      }else if(updatedReaction.reaction === "Love"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Love' : admin.firestore.FieldValue.increment(1)
        });
      }else if(updatedReaction.reaction === "Haha"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Haha' : admin.firestore.FieldValue.increment(1)
        });
      }else if(updatedReaction.reaction === "Wow"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Wow' : admin.firestore.FieldValue.increment(1)
        });
      }else if(updatedReaction.reaction === "Sad"){
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Sad' : admin.firestore.FieldValue.increment(1)
        });
      }else{
        db.doc('PostsCollection/'+context.params.postID).update({
          'numberOfReacts.Angry' : admin.firestore.FieldValue.increment(1)
        });
      }
    }
    return null;
  });
    
exports.listenToDeletedReactions = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}/Reactions/{reactID}') 
  .onDelete((snap, context) => {
    
    var deletedReact = snap.data();

    if(deletedReact.reaction === "Like"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Like' : admin.firestore.FieldValue.increment(-1)
      });
    }else if(deletedReact.reaction === "Love"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Love' : admin.firestore.FieldValue.increment(-1)
      });
    }else if(deletedReact.reaction === "Haha"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Haha' : admin.firestore.FieldValue.increment(-1)
      });
    }else if(deletedReact.reaction === "Wow"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Wow' : admin.firestore.FieldValue.increment(-1)
      });
    }else if(deletedReact.reaction === "Sad"){
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Sad' : admin.firestore.FieldValue.increment(-1)
      });
    }else{
      db.doc('PostsCollection/'+context.params.postID).update({
        'numberOfReacts.Angry' : admin.firestore.FieldValue.increment(-1)
      });
    }

    db.doc('PostsCollection/'+context.params.postID).update({
      numberOfReactsCounter : admin.firestore.FieldValue.increment(-1)
    });

    return null;
  });
// ------------------------------------------------------------------
  
// -- Triggers for Posts Actions
exports.listenToPostsCreates = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}')
  .onCreate((snap, context) => {
    
    // -- created document
    var createdDocument = snap.data();

    // -- get Friends of the author
    db.collection('FriendsCollection/'+createdDocument.authorUID+'/Friends').get()
      .then(querySnapshot=>{

        // Get a new write batch
        const batch = db.batch();
        // -- first, add it to the author Feeds
        var docRef = db.doc('FeedsCollection/'+createdDocument.authorUID+'/Posts/'+context.params.postID);
        batch.set(docRef, createdDocument);
        // -- then add to author friends feeds
        querySnapshot.forEach(docSnapshot =>{
          const ref = db.doc('FeedsCollection/'+docSnapshot.id+'/Posts/'+context.params.postID);
          console.log("id is: "+docSnapshot.id);
          batch.set(ref, createdDocument);
        });
        batch.commit();
        return null;
      }).catch(error=>{
        console.log(error);
      });
    return null;
});

exports.listenToPostsUpdates = functions
  .region('asia-east2')
  .firestore.document('PostsCollection/{postID}')
  .onUpdate((change, context) => {
    // -- document changed
    var updatedDocument = change.after.data();
    return db.collection('FriendsCollection/'+updatedDocument.authorUID+'/Friends').get()
      .then(querySnapshot=>{

        // -- loop thru friendsList
        // Get a new write batch
        const batch = db.batch();
        // -- first, update the author Posts
        var docRef = db.doc('FeedsCollection/'+updatedDocument.authorUID+'/Posts/'+context.params.postID);
        batch.update(docRef, updatedDocument);
        // -- then update the author friends feeds
        querySnapshot.forEach(docSnapshot =>{
          const ref = db.doc('FeedsCollection/'+docSnapshot.id+'/Posts/'+context.params.postID);
          batch.update(ref, updatedDocument);
        });
        batch.commit();
        return null;
      }).catch(error=>{
        console.log(error);
    });
});

// --- Recursive Delete of new Posts
exports.recursiveDelete = functions
  .runWith({
    timeoutSeconds: 540,
    memory: '2GB'
  })
  .https.onCall(async (data, context) => {
    // // Only allow admin users to execute this function.
    // if (!(context.auth && context.auth.token && context.auth.token.admin)) {
    //   throw new functions.https.HttpsError(
    //     'permission-denied',
    //     'Must be an administrative user to initiate delete.'
    //   );
    // }

    const path = data.path;
    console.log(
      `User ${context.auth.uid} has requested to delete path ${path}`
    );

    // Run a recursive delete on the given document or collection path.
    // The 'token' must be set in the functions config, and can be generated
    // at the command line by running 'firebase login:ci'.
    await firebase_tools.firestore
      .delete(path, {
        project: process.env.GCLOUD_PROJECT,
        recursive: true,
        yes: true,
        token: '1//0eh1ZTi8V49a8CgYIARAAGA4SNwF-L9Ir5w3WZx6BwVnUxi99xnFRskHEysHBdbWtj9hcWNEZu-1cABvZuAH9QdM79xzj9az2JQQ'
      });

    return {
      path: path 
    };
  });