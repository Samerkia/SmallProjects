using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RoomBehaviour : MonoBehaviour
{

    public GameObject[] walls; // 0 - up : 1 - down : 2 - Right : 3 - Left
    public GameObject[] doors; // 0 - up : 1 - down : 2 - Right : 3 - Left


    public void UpdateRoom(bool[] status)
    {
        for (int i = 0; i < status.Length; i++)
        {
            doors[i].SetActive(status[i]);
            walls[i].SetActive(!status[i]);
        }
    }
}
