using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Teleport : MonoBehaviour
{
    /*[SerializeField]
    private Transform teleportTarget;
    [SerializeField]
    private GameObject Player;
    [SerializeField]
    private GameObject playerCam;*/
    [SerializeField]
    private GameObject PortalLock;

    void OnTriggerEnter(Collider col)
    {
        //Player.transform.position = teleportTarget.transform.position;
        if(!PortalLock.activeSelf)
        {
            if (col.gameObject.CompareTag("Player"))
            {
                Debug.Log("Teleported");
                col.gameObject.SetActive(false);
                col.gameObject.transform.position = new Vector3(-191, 0, 227);
                col.gameObject.SetActive(true);
            }
        }
    }
}