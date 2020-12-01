const functions = require('firebase-functions');
const firebase_tools = require('firebase-tools');
var admin = require('firebase-admin');
const db = admin.firestore();


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